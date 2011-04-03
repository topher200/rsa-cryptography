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

