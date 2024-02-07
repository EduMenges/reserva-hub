<script lang="ts">
    import { Alert } from "@sveltestrap/sveltestrap";

    export let message: App.Superforms.Message | undefined;
</script>

{#if message}
    {#if typeof message === "string"}
        <Alert color="success" class="mt-2">{message}</Alert>
    {:else}
        <Alert color="danger" class=mt-2>
            Erro
            <ul>
                {#each message.errors as error}
                    <li class="text-danger">{error}</li>
                {/each}
            </ul>
        </Alert>
    {/if}
{/if}
