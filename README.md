# jetstreem

Implementation of streem language running on JVM. (currently using RxJava)

This is inspired by streem(https://github.com/matz/streem).


## Installation

To build,
```
./gradlew installDist
```

## Usage

Go to the bin directory,
```
cd build/install/jetstreem/bin 
```

To run,
```
./jetstreem {streem script}
```

To check AST,
```
./jetstreem -d ast {streem script}
```

To check AST and IR,
```
./jetstreem -d ir {streem script}
```

For help,
```
./jetstreem -h
```

## Current status

Please check wiki(https://github.com/koji-m/jetstreem/wiki).

