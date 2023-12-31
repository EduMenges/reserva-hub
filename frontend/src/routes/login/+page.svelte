<script lang="ts">
  import Logo from "$lib/components/Logo.svelte";
</script>

<Logo />
<p>Entre com a sua conta pessoal do INF</p>

<form method="post">
  <div class="mb-3">
    <label class="form-label" for="username">Usuário</label>
    <input id="username" class="form-control" type="text" />
  </div>
  <div class="mb-3">
    <label class="form-label" for="password">Senha</label>
    <input id="password" class="form-control" type="password" />
  </div>

  <button type="submit" class="btn btn-primary" formaction="?/login">Entrar</button>
  <button type="submit" class="btn btn-danger" formaction="?/as_admin">Entrar como administrador</button>
</form>
