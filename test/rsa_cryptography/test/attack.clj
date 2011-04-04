(ns rsa-cryptography.test.attack
  (:use [rsa-cryptography.attack] :reload)
  (:use [clojure.test]))

(deftest generate-keys-list-test
  (let [keys {:d 399707681, :e 65537, :modulus 3306810623}]
    (is (= (generate-keys-list keys 100 3)
           '({:d 100, :e 65537, :modulus 3306810623}
             {:d 101, :e 65537, :modulus 3306810623}
             {:d 102, :e 65537, :modulus 3306810623})))))
