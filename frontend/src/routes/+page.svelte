<script lang="ts">
  import Logo from "$lib/components/Logo.svelte";
  import SmallContainer from "$lib/components/SmallContainer.svelte";
</script>

<svelte:head>
    <title>
        ReservaHub
    </title>
</svelte:head>

<SmallContainer>
  <header>
    <Logo class="text-center" />
  </header>
  
  <hr />

  <div class="d-flex justify-content-between flex-wrap">
    <a href="book" class="btn btn-primary" role="button">Fazer uma reserva</a>
    <a href="history" class="btn btn-secondary" role="button">Suas reservas</a>
  </div>
</SmallContainer>
