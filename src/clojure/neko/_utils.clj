; Copyright © 2011 Sattvik Software & Technology Resources, Ltd. Co.
; All rights reserved.
;
; This program and the accompanying materials are made available under the
; terms of the Eclipse Public License v1.0 which accompanies this distribution,
; and is available at <http://www.eclipse.org/legal/epl-v10.html>.
;
; By using this software in any fashion, you are agreeing to be bound by the
; terms of this license.  You must not remove this notice, or any other, from
; this software.

(ns neko.-utils
  "Internal utilities used by Neko, not intended for external consumption."
  {:author "Daniel Solano Gómez"})

(defn static-field-value
  "Takes a keyword and converts it to a field name by getting the name from the
  keyword, converting all hypens to underscores, capitalizing all letters, and
  applying the transformation function."
  ([^Class class field xform]
   {:pre [(class? class)
          (keyword? field)
          (fn? xform)]}
   (let [field (.. (name field) (replace \- \_) toUpperCase)
         field ((fn [x] {:post [(string? %)]} (xform x)) field)
         field (.getField class ^String field)]
     (.get field nil)))
  ([class field]
   (static-field-value class field identity)))

(defn integer-or-keyword?
  "Convenient method for testing if the argument is an integer or a keyword."
  [x]
  (or (integer? x)
      (keyword? x)))

(defn simple-name [full-class-name]
  "Takes a possibly package-qualified class name symbol and returns a
  simple class name from it."
  (nth (re-find #"(.*\.)?(.+)" (str full-class-name)) 2))

(defn capitalize
  "Takes a string and upper-cases the first letter in it."
  [s]
  (str (.toUpperCase (.substring s 0 1)) (.substring s 1)))
