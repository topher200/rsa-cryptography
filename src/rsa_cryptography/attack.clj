(ns rsa-cryptography.attack
  (:use [rsa-cryptography.core] :reload))

(defn message-round-trip
  [keys message]
  (decrypt-message (encrypt-message message (:e keys) (:modulus keys))
                   (:d keys) (:modulus keys)))

(defn unchanged-round-trip
  ([] (unchanged-round-trip (generate-keys 16)))
  ([keys] (unchanged-round-trip keys (rand-int 25600)))
  ([keys message] (= message (message-round-trip keys message))))

(defn generate-keys-list
  "Creates a list of _num_ keys based on _keys_, incrementing :d from _d-start_"
  ([] (generate-keys-list (generate-keys 16)))
  ([keys] (generate-keys-list keys (- (:d keys) 5) 10))
  ([keys d-start num]
     (map #(assoc keys :d %) (take num (iterate inc d-start)))))

