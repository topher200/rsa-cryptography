(ns rsa-cryptography.core)

(defn extended-gcd
  "Returns the [x y] for the extended Euclidean algorithm. Found here:
  http://en.wikipedia.org/wiki/Extended_Euclidean_algorithm"
  [a b]
  (if (= (mod a b) 0)
    [0 1]
    (let [[x y] (extended-gcd (b (mod a b)))]
      [y (- x (* y (/ a b)))])))

(println (str (extended-gcd 3120 17)))
