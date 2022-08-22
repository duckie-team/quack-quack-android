### 1. tar 압축

```
tar cvf secrets.tar buildSrc/src/main/kotlin/BuildConstants.kt keystore
```

### 2. GPG 암호화

```
gpg -c secrets.tar
```
