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

(defn prime? [n]
  (not-any? #(= (mod n %) 0) (take (sqrt n) (iterate inc 2))))

(defn find-prime [max]
  "Determines a prime n: (max - sqrt(max)) < n < max"
  (let [possible-prime (- max (rand-int (sqrt max)))]
    (if (prime? possible-prime)
      possible-prime
      (find-prime max)))) ;; todo(topher)- should be loop/recur

(defn generate-totient
  "RSA Wikipedia example, step 3"
  [p q] (* (- p 1) (- q 1)))

(defn generate-encrypt-key
  "Determines a prime encrypt key (e) that satisfies:
     A. 1 < e < totient
     B. coprime to the totient"
  [totient]
  (let [e (find-prime totient)]
    (if (not= e totient) e (generate-encrypt-key totient))))

(defn generate-keys []
  (let [[p q] (repeatedly 2 #(find-prime 100))
        modulus (* p q)
        totient (generate-totient p q)
        encrypt-key (generate-encrypt-key totient)
        decrypt-key (modular-multiplicative-inverse encrypt-key totient)]
    [modulus encrypt-key decrypt-key]))

(defn encrypt-message
  [message modulus encrypt-key]
  (mod (expt message encrypt-key) modulus))

(defn decrypt-message
  [message modulus decrypt-key]
  (mod (expt message decrypt-key) modulus))
