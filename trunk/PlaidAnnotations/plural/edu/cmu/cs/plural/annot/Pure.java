/**
 * Copyright (c) 2006-2008 by Carnegie Mellon University and others.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *    1. Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *    2. Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *    3. Neither the names of the authors nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHORS ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.cmu.cs.plural.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Pure permissions grant read-only access but <i>assume that other permissions are
 * allowed to modify the referenced object</i>.
 * 
 * @author Kevin Bierhoff
 * @see Pures To aggregate this annotation.
 * @see ResultPure To annotate a method result.
 * @see Perm For more complex method specifications.
 */
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.FIELD})
public @interface Pure {

	/** 
	 * Default attribute for root node (state guarantee), defaults to <i>alive</i>.
	 * @see #guarantee() explicitly named attribute for the same information
	 */
	String value() default "alive";
	
	/** 
	 * Better-named attribute for root node (state guarantee).  Takes precedence
	 * over {@link #value()}.
	 * @see #value() default attribute for the same information
	 */
	String guarantee() default "";
	
	/**
	 * By default, permission will be returned from method.  Set to
	 * <code>false</code> for consumed permissions. 
	 */
	boolean returned() default true;
	
	/**
	 * Indicates whether this permission can be used to access fields
	 * or to make virtual method calls (default).
	 */
	boolean fieldAccess() default false;
	
	/**
	 * Explicit fractions associated with permission (optional). 
	 */
	String fract() default "";
	
	/**
	 * State information when method is invoked.
	 */
	String[] requires() default { };

	/**
	 * State information when method returns.
	 */
	String[] ensures() default { };
	
}