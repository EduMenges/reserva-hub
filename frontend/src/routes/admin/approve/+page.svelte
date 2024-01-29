<script lang="ts">
    import { enhance } from "$app/forms";
    import Date from "$lib/components/table/Date.svelte";
    import TimeSpan from "$lib/components/table/TimeSpan.svelte";
    import { readableEntityType } from "$lib/utils";

    export let data;
    let entriesWaitingApproval = data.history.filter((entry) => entry.status === "AWAITING_APPROVAL");
</script>

<svelte:head>
    <title>Aprovar Reservas</title>
</svelte:head>

{#if entriesWaitingApproval.length > 0}
<section class="table-responsive">
    <table class="table table-hover table-striped">
        <thead>
            <tr>
                <th scope="col">Usuário</th>
                <th scope="col">Data</th>
                <th scope="col">Horário</th>
                <th scope="col">Nome</th>
                <th scope="col">Sala</th>
                <th scope="col">Tipo</th>
                <th scope="col">Ações</th>
            </tr>
        </thead>
        <tbody>
            {#each entriesWaitingApproval as entry}
                <tr>
                    <td>
                        {entry.userInfo.username}
                    </td>
                    <Date date={entry.startDate} />
                    <TimeSpan startTime={entry.startDate} endTime={entry.endDate} />
                    <td>
                        {entry.eventName}
                    </td>
                    <td>
                        {entry.roomInfo.buildingNumber} — {entry.roomInfo.roomNumber}
                    </td>
                    <td>
                        {readableEntityType(entry.entryMapping.type)}
                    </td>
                    <td>
                        <form method="post" use:enhance>
                            <input readonly name="entityId" value={entry.entryMapping.entityId} hidden />
                            <input readonly name="userId" value={entry.userInfo.userId} hidden />
                            <button class="btn btn-success" type="submit" formaction="?/postApprove">Aprovar</button>
                            <button class="btn btn-danger" type="submit" formaction="?/postDelete">Negar</button>
                        </form>
                    </td>
                </tr>
            {/each}
        </tbody>
    </table>
</section>
{:else}
<p class="text-center">Não há reservas esperando aprovação.</p>
{/if}