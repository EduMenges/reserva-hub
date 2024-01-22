<script lang="ts">
    import { type Error } from "$lib/ApiTypes";

    export let message: Error | undefined;
</script>

{#if message}
    <ul>
        {#each message.errors as error}
            <li class="text-danger ">{error}</li>
        {/each}
    </ul>
{/if}
