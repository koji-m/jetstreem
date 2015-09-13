# JetStreem

This is inspired by https://github.com/matz/streem
(and by Takao Oosawa)

YAPC Asia 2015 gave me motive.

## Sample Code of Streem lang

```
seq(100) | {x ->
  if x % 15 == 0 {
    "FizzBuzz"
  }
  else if x % 3 == 0 {
    "Fizz"
  }
  else if x % 5 == 0 {
    "Buzz"
  }
  else {
    x
  }
} | STDOUT
```
## Installation

To build parser,
```
make
```

To build core,
```
make core
```
core operates same as following code.
```
stdin | to_upper() | stdout
```

## Usage

To check syntax,
```
./jetstreem hoge.strm
```

To test core (operates described above),
```
cat some_text_file | java pw/koj/jetstreem/Main
```

