(ns rsa-cryptography.core
  (:use [clojure.contrib.math]))

(defn big-integer [int]
  ;; todo(topher): there must be a better way than int -> string -> BigInteger
  (new BigInteger (str int)))

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
    (big-integer (mod x m))))

(defn find-prime [bit-length]
  (. BigInteger (probablePrime bit-length (new java.util.Random))))

(defn generate-totient
  "RSA Wikipedia example, step 3"
  [p q] (* (- p 1) (- q 1)))

(defn generate-encrypt-key
  "Determines a prime encrypt key (e) that satisfies:
     A. 1 < e < totient
     B. coprime to the totient"
  [totient]
  (let [e (big-integer 65537)]
    (if (not= 0 (mod totient e)) e)))

(defn generate-keys [bit-length]
  (let [[p q] (repeatedly 2 #(find-prime bit-length))
        modulus (. p (multiply q))
        totient (generate-totient p q)
        encrypt-key (generate-encrypt-key totient)
        decrypt-key (modular-multiplicative-inverse encrypt-key totient)]
    [modulus encrypt-key decrypt-key]))

(defn encrypt-message-big-integer
  [message encrypt-key modulus]
  (. message (modPow encrypt-key modulus)))

(defn encrypt-message
  [message encrypt-key modulus]
  (apply encrypt-message-big-integer
         (map big-integer [message encrypt-key modulus])))

(defn decrypt-message-big-integer
  [message decrypt-key modulus]
  (. message (modPow decrypt-key modulus)))

(defn decrypt-message
  [message decrypt-key modulus]
  (apply decrypt-message-big-integer
         (map big-integer [message decrypt-key modulus])))
