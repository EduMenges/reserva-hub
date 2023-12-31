<script>
  import Logo from "$lib/components/Logo.svelte";
  import SmallContainer from "$lib/components/SmallContainer.svelte";
</script>

<SmallContainer>
  <header>
    <Logo class="text-center" textClass="text-danger" />
  </header>

  <hr />

  <div class="d-flex justify-content-between">
    <a class="btn btn-primary" href="admin/approve" role="button">Aprovar reservas</a>
    <a class="btn btn-secondary" href="admin/history" role="button">Histórico de reservas</a>
  </div>
</SmallContainer>
