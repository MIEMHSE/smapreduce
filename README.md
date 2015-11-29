# smapreduce

**Simple MapReduce implementation in Clojure**

Author: The Profitware Group / Sergey Sobko <S.Sobko@profitware.ru>

The code is mainly written as a part of my very special coursework at 
Moscow Institute of Electronics and Mathematics (MIEM HSE).

## Usage

```
# Map REPL instance on port 1088
$ lein run map 1088
# Map REPL instance on port 1099
$ lein run map 1099
# MapReduce client
$ lein run reduce "+" "#(+ 3 %)" "(1 2 3 4)" 1088 1099
```
