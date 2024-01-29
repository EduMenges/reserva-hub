<script lang="ts">
    import type { EntryStatus } from "$lib/ApiTypes";

    export let status: EntryStatus;
</script>

{#if status === "APPROVED"}
    <span class="text-success-emphasis">Aprovado</span>
{:else if status === "AWAITING_APPROVAL"}
    <span class="text-warning-emphasis">Aguardando aprovação</span>
{:else if status === "CANCELED"}
    <span class="text-danger-emphasis">Cancelado</span>
{:else if status === "ACTIVE"}
    <span class="text-success-emphasis">Ativo</span>
{:else if status === "DENIED"}
    <span class="text-danger-emphasis">Negado</span>
{:else if status === "EXPIRED"}
    <span class="text-danger-emphasis">Expirado</span>
{/if}
