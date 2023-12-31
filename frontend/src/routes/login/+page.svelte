<script lang="ts">
  import Logo from "$lib/components/Logo.svelte";
  import { enhance } from "$app/forms";
  import SmallContainer from "$lib/components/SmallContainer.svelte";
</script>

<SmallContainer>
  <div class="text-center">
    <Logo />
    <p>Entre com a sua conta pessoal do INF</p>
  </div>

  <form method="post" use:enhance>
    <div class="mb-3">
      <label class="form-label" for="username">Usuário</label>
      <input id="username" class="form-control" type="text" />
    </div>
    <div class="mb-3">
      <label class="form-label" for="password">Senha</label>
      <input id="password" class="form-control" type="password" />
    </div>

    <div class="d-flex justify-content-between flex-wrap">
      <button type="submit" class="btn btn-primary" formaction="?/login">Entrar</button>
      <button type="submit" class="btn btn-danger" formaction="?/as_admin">Entrar como administrador</button>
    </div>
  </form>
</SmallContainer>
