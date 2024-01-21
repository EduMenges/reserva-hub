<script lang="ts">
	export let errors: string[] | undefined;
</script>

{#if errors}
<div class="invalid-feedback">
    {#each errors as error}
        {error}
    {/each}
</div>
{/if}