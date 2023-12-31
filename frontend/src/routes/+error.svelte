<script lang="ts">
    import { page } from '$app/stores';
</script>

<section class="text-center ">
<h1>Ocorreu um <span class="text-danger">erro</span></h1>
<p>{$page.status}: {$page.error?.message}<p>
</section>