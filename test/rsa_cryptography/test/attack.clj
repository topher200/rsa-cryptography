(ns rsa-cryptography.test.attack
  (:use [rsa-cryptography.core] :reload)
  (:use [rsa-cryptography.attack] :reload)
  (:use [clojure.test]))

(def test-keys {:d 399707681, :e 65537, :modulus 3306810623})

(deftest rand-big-int-test
  (is (> 1000000 (rand-big-int 1000000))))

(deftest message-round-trip-test
  (is (message-round-trip (generate-keys 16) 1001) 1001))

(deftest unchanged-round-trip-test
  (is (unchanged-round-trip)))

(deftest generate-keys-list-test
  (is (= (take 3 (generate-keys-list test-keys 100))
         '({:d 100, :e 65537, :modulus 3306810623}
           {:d 101, :e 65537, :modulus 3306810623}
           {:d 102, :e 65537, :modulus 3306810623}))))

(deftest check-solution-keys-test
  (is (check-solution-keys
       (take 1 (generate-keys-list test-keys (:d test-keys)))))
  (is (not (check-solution-keys (take 1 (generate-keys-list test-keys 100))))))
