/*
 *
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
package com.novus.salat.annotations.raw;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Apply to a method on a companion Object to denote it as the factory for Salat to use when constructing an instance.
 * <p/>
 * {@code
 *  import com.novus.salat.annotations._
 *
 * case class Employee(id: EmployeeNumber, role: Role, name: String)
 *
 * object Employee {
 *  @SalatFactory
 *  def apply(id: String, role: String, name: String) = Employee(EmployeeNumber.valueOf(id), Role.valueOf(role), name)
 * }
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SalatFactory {}
