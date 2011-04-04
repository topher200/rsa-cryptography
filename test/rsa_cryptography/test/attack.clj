(ns rsa-cryptography.test.attack
  (:use [rsa-cryptography.attack] :reload)
  (:use [clojure.test]))

(def test-keys {:d 399707681, :e 65537, :modulus 3306810623})

(deftest generate-keys-list-test
  (is (= (generate-keys-list test-keys 100 3)
         '({:d 100, :e 65537, :modulus 3306810623}
           {:d 101, :e 65537, :modulus 3306810623}
           {:d 102, :e 65537, :modulus 3306810623}))))

(deftest check-solution-keys-test
  (is (check-solution-keys (generate-keys-list test-keys)))
  (is (not (check-solution-keys (generate-keys-list test-keys 100 1)))))
