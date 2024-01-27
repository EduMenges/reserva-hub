<script lang="ts">
    import type * as Api from "$lib/ApiTypes"
    export let message: Api.Error | undefined;
</script>

{#if message}
    <ul>
        {#each message.errors as error}
            <li class="text-danger ">{error}</li>
        {/each}
    </ul>
{/if}
