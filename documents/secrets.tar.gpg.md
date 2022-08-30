# 암호화

### 1. tar 압축

```
tar cvf secrets.tar buildSrc/src/main/kotlin/BuildConstants.kt keystore
```

### 2. GPG 암호화

```
gpg -c secrets.tar
```

# 복호화

### 1. GPG 복호화

```
gpg --quiet --batch --yes --always-trust --decrypt --passphrase=${{secrets.SECRET_GPG_PASSWORD}} --output secrets.tar secrets.tar.gpg
```

### 2. tar 압축 해제

```
tar xvf secrets.tar
```

