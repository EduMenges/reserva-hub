<script lang="ts">
  import Logo from "$lib/components/Logo.svelte";
  import SmallContainer from "$lib/components/SmallContainer.svelte";
</script>

<SmallContainer>

<form method="post">
  <div class="mb-3">
    <label class="form-label" for="username">Usuário</label>
    <input id="username" class="form-control" type="text" />
  </div>
  <div class="mb-3">
    <label class="form-label" for="password">Senha</label>
    <input id="password" class="form-control" type="password" />
  </div>

</SmallContainer>
