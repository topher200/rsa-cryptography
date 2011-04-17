# RSA Crytography- Key generator and message encrypt/decrypt

## Introduction
   RSA Cryptography provides a RSA key generator and functions to use RSA keys 
   to encrypt and decrypt numeric messages.

   Problem from Programming Praxis:
   http://programmingpraxis.com/2010/11/16/rsa-cryptography/


## Usage 
   The first step is to make some keys. Import the project, then call 
   generate-keys. You need to specify your key's primes' bit-length.

   (ns repl (:use [rsa-cryptography.core]))
   (def my-keys (generate-keys 16))

   Now that you have keys, you can use them to manipulate messages.

   (encrypt-message 100100100 (:e my-keys) (:modulus my-keys))
   >>> 124800500
   (decrypt-message 124800500 (:d my-keys) (:modulus my-keys))
   >>> 100100100 

 
## Changelog 
  v1.00: Initial release!


## Blog post
  A short blog post about this problem is available here:
  http://blog.tophernet.com/2011/04/rsa-cryptography-in-clojure.html

  The source for this project is available on Github:
  https://github.com/topher200/rsa-cryptography


## License
  Copyright Topher Brown <topher200@gmail.com>, 2011. Released under the MIT 
  license.
