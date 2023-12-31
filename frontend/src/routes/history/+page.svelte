<script lang="ts">
  import StatusColored from "$lib/components/StatusColored.svelte";
  import { Status } from "$lib/schemas";
  import type { PageServerData } from "./$types";
  export let data: PageServerData;

  function hours(date: Date) {
    return date.toLocaleTimeString([], { timeStyle: "short" });
  }
</script>

{#if data.bookings.length > 0}
  <section class="table-responsive">
    <table class="table table-hover table-striped">
      <thead>
        <tr>
          <th scope="col">Data</th>
          <th scope="col">Horário</th>
          <th scope="col">Nome</th>
          <th scope="col">Sala</th>
          <th scope="col">Status</th>
          <th scope="col">Editar</th>
        </tr>
      </thead>
      <tbody>
        {#each data.bookings as booking (booking.startDate)}
          <tr>
            <td>
              {booking.startDate.toLocaleDateString()}
            </td>
            <td>
              {hours(booking.startDate)} — {hours(booking.endDate)}
            </td>
            <td>
              {booking.name}
            </td>
            <td>
              {booking.room}
            </td>
            <td>
              <StatusColored status={booking.status} />
            </td>
            <td>
              <button class="btn btn-secondary" disabled={booking.status === Status.Canceled}>Editar</button>
            </td>
          </tr>
        {/each}
      </tbody>
    </table>
  </section>
{:else}
  <p>Você não possui reservas.</p>
{/if}
