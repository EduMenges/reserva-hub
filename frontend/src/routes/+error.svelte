<script lang="ts">
    import { page } from '$app/stores';
</script>

<section class="text-center ">
<h1>Ocorreu um erro</h1>
<p>{$page.status}: {$page.error?.message}<p>
</section>