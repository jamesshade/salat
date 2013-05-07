/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Forked from:
 *
 * Project:      http://github.com/novus/salat
 * Wiki:         http://github.com/novus/salat/wiki
 * Mailing list: http://groups.google.com/group/scala-salat
 */
package com.novus.salat

import com.novus.salat.annotations.raw.SalatFactory
import com.novus.salat.util.BestAvailableConstructor
import java.lang.reflect.{ GenericDeclaration, Constructor, Method }

object SalatConstructor {

  def forClass[A](clazz: Class[A], companionClass: Class[_], companionObject: AnyRef): SalatConstructor[A] = {
    companionClass.getMethods.find(m => m.getAnnotation(classOf[SalatFactory]) != null) match {
      case Some(method) => new SalatFactoryWrapper[A](companionObject, method)
      case _            => new SalatConstructorWrapper[A](BestAvailableConstructor(clazz))
    }
  }
}

trait SalatConstructor[A] extends GenericDeclaration {

  protected def underlying: GenericDeclaration

  def description: String
  def genericString: String
  def genericParameterTypes: Array[java.lang.reflect.Type]

  def invoke(args: Seq[AnyRef]): A

  override def getTypeParameters = underlying.getTypeParameters

}

class SalatConstructorWrapper[A](override protected val underlying: Constructor[A]) extends SalatConstructor[A] {

  override lazy val description = "CONSTRUCTOR"
  override lazy val genericString = underlying.toGenericString
  override lazy val genericParameterTypes = underlying.getGenericParameterTypes

  override def invoke(args: Seq[AnyRef]): A = underlying.newInstance(args: _*)
}

class SalatFactoryWrapper[A](companionObject: AnyRef, override protected val underlying: Method) extends SalatConstructor[A] {

  override lazy val description = s"FACTORY METHOD(${underlying.getName})"
  override lazy val genericString = underlying.toGenericString
  override lazy val genericParameterTypes = underlying.getGenericParameterTypes

  override def invoke(args: Seq[AnyRef]): A = underlying.invoke(companionObject, args: _*).asInstanceOf[A]
}
