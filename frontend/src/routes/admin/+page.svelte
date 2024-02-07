<script>
    import { BootstrapColors } from "$lib/ApiTypes.d";
    import Logo from "$lib/components/Logo.svelte";
    import SmallContainer from "$lib/components/SmallContainer.svelte";
</script>

<SmallContainer>
    <header>
        <Logo class="text-center" textColor={BootstrapColors.DANGER} />
    </header>

    <hr />

    <div class="d-flex justify-content-between">
        <a class="btn btn-primary" href="admin/approve" role="button">Pedidos Pendentes</a>
        <a class="btn btn-secondary" href="admin/history" role="button">Histórico global</a>
    </div>
</SmallContainer>
