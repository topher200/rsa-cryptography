(ns rsa-cryptography.test.core
  (:use [rsa-cryptography.core] :reload)
  (:use [clojure.test]))

(deftest division-with-remainder-test
  (is (= [0 0] (division-with-remainder 0 1)))
  (is (= [1 0] (division-with-remainder 1 1)))
  (is (= [0 1] (division-with-remainder 1 2)))
  (is (= [0 1] (division-with-remainder 1 3)))
  (is (= [0 2] (division-with-remainder 2 3)))
  (is (= [1 1] (division-with-remainder 3 2)))
  (is (= [2 2] (division-with-remainder 8 3))))

(deftest extended-gcd-test
  (is (= [1 0] (extended-gcd 1 0)))
  (is (= [-9 47] (extended-gcd 120 23)))
  (is (= [-367 2] (extended-gcd 17 3120))))

(deftest modular-multiplicative-inverse-test
  (is (= 2753 (modular-multiplicative-inverse 17 3120))))

(deftest generate-totient-test
  (is (= 3120 (generate-totient 61 53))))

(deftest encrypt-message-test
  (is (= 2790 (encrypt-message 65 17 3233))))

(deftest decrypt-message-test
  (is (= 65 (decrypt-message 2790 2753 3233))))

(deftest encrypt-and-decrypt-test
  (is (= 65 (decrypt-message (encrypt-message 65 17 3233) 2753 3233))))

(deftest encrypt-and-decrypt-random-message-test
  (let [[modulus e d] (generate-keys 16)
        message (rand-int 100000)]
    (is (= message (decrypt-message (encrypt-message message e modulus)
                                    d modulus)))))
