<script lang="ts">
    import  StatusColored  from "$lib/components/StatusColored.svelte"

    export let data;
</script>

<section class="table-responsive">
    <table class="table table-hover table-striped">
        <thead>
            <tr>
                <th scope="col">Usuário</th>
                <th scope="col">Data</th>
                <th scope="col">Horário</th>
                <th scope="col">Nome</th>
                <th scope="col">Sala</th>
                <th scope="col">Status</th>
            </tr>
        </thead>
        <tbody>
            {#each data.history.filter(entry => entry.status !== "AWAITING_APPROVAL") as entry}
                <tr>
                    <td>
                        {entry.userInfo.username}
                    </td>
                    <td>
                        {entry.date}
                    </td>
                    <td>
                        {entry.startTime.slice(0, 5)} — {entry.endTime.slice(0, 5)}
                    </td>
                    <td>
                        {entry.eventName}
                    </td>
                    <td>
                        {entry.roomInfo.buildingNumber} — {entry.roomInfo.roomNumber}
                    </td>
                    <td>
                        <StatusColored status={entry.status} />
                    </td>
                </tr>
            {/each}
        </tbody>
    </table>
</section>