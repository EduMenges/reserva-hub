<script lang="ts">
    import { page } from "$app/stores";
</script>

<svelte:head>
	<title>{$page.status}</title>
</svelte:head>

<section class="text-center">
    <h1>Ocorreu um <span class="text-danger">erro</span></h1>
    <p>{$page.status}: {$page.error?.message}</p>
</section>
