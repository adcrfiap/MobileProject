# MobileProject

## Pré-requisito

* Android Studio
* Conta no google firebase

### Firebase
* Ativar o metodo de autenticação por e-mail e senha
* Ativar o Cloud Firestore

### Android Studio
* Necessario comentar as linhas abaixo que estão no arquivo build.gradle
    implementation 'com.google.firebase:firebase-auth:20.0.0'
    implementation 'com.google.firebase:firebase-firestore-ktx:22.0.0'
* Executar o build do projeto    
* Na barra de ferramentas, navegar para Tools->Firebase para conectar a sua conta do firebase
* Remover os comenentários inseridos no arquivo build.gradle no primeiro passo e efetuar o build novamente
* Executar aplicação
