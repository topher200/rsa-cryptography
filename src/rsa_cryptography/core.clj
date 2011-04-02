(ns rsa-cryptography.core
  (:use [clojure.contrib.math]))

(defn division-with-remainder [x y]
  [(floor (/ x y)) (mod x y)])

(defn extended-gcd
  "Returns the [x y] for the extended Euclidean algorithm. Found here:
  http://en.wikipedia.org/wiki/Extended_Euclidean_algorithm"
  [a b]
  (if (= b 0)
    [1 0]
    (let [[q r] (division-with-remainder a b)
          [s t] (extended-gcd b r)]
      [t (- s (* q t))])))

(defn modular-multiplicative-inverse
  "Determines the inverse cooresponding to a given a with respect to modules m.
  Research: http://en.wikipedia.org/wiki/Modular_multiplicative_inverse and
  http://numericalrecipes.blogspot.com/2009/03/modular-multiplicative-inverse.html"
  [a m]
  (let [[x y] (extended-gcd a m)]
    (mod x m)))
