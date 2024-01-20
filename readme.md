# Compilação

## Backend

É necessário a instalação do PostgreSQL e PgAdmin para rodar a base de dados. O servidor, por padrão, se conecta na DB com nome `reservahub`, que roda no hostname `localhost`. O nome da base de dados pode ser alterado, contanto que a modificação se propague para o arquivo `src/main/resources/application.properties`. No mesmo arquivo, modifique a senha para se conectar à database, utilizando a sua senha mestra. **Não commite sua senha mestra**.

Para rodar, basta executar `./gradlew bootRun`. Caso você encontre um erro ao rodar em distros Linux, pode ser que isso seja por falta de permissões para executar o arquivo. Para consertar, basta fazer um `chmod +x gradlew`.

### Postman

Para facilitar o desenvolvimento, nós usamos o Postman, que contém uma boa documentação das possíveis chamadas e endpoints do servidor. Basta criar uma conta e instalar o aplicativo na sua máquina. Também é necessário se conectar ao workspace. Para isso, contate o João Paulo.

### Autenticação

Todos os endpoints são bloqueados por padrão e precisam de um bearer token, exceto, é claro, pelo login.

## Frontend

É necessário ter o `pnpm` instalado. Para rodar a versão dev com hot-reload, basta fazer `pnpm dev`. Para compilar, basta fazer `pnpm build`.