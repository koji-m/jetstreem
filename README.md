# jetstreem

Implementation of streem language running on JVM. (currently using RxJava)

This is inspired by streem(https://github.com/matz/streem).


## Installation

To build,
```
./gradlew assemleDist
```

(to put distribution on the appropriate place and untar.)
```
cp build/distribution/jetstreem.tar {target dir}
cd {target dir}; tar xf jetstreem.tar
```

## Usage

To run,
```
bin/jetstreem {streem script}
```

To check AST,
```
bin/jetstreem -d ast {streem script}
```

To check AST and IR,
```
bin/jetstreem -d ir {streem script}
```

For help,
```
bin/jetstreem -h
```

## Current status

Please check wiki(https://github.com/koji-m/jetstreem/wiki).

