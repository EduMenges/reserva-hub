<script lang="ts">
    import { enhance } from "$app/forms";
    import { readableEntityType } from "$lib/utils";

    export let data;
</script>

<svelte:head>Aprovar reservas</svelte:head>

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
                <th scope="col">Aprovar</th>
                <th scope="col">Negar</th>
            </tr>
        </thead>
        <tbody>
            {#each data.history.filter((entry) => entry.status === "AWAITING_APPROVAL") as entry (entry.startTime)}
                <tr>
                    <td>
                        {entry.userInfo.username}
                    </td>
                    <td>
                        {entry.date}
                    </td>
                    <td>
                        {entry.startTime} — {entry.endTime}
                    </td>
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
                            <button class="btn btn-success">aa </button>
                        </form>
                    </td>
                    <td>
                        <form method="post" use:enhance >
                            <button class="btn btn-danger">aa</button>
                        </form>
                    </td>
                </tr>
            {/each}
        </tbody>
    </table>
</section>
