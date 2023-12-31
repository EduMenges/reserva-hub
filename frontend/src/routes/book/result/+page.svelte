<script lang="ts">
    import { enhance } from "$app/forms";
  import type { RoomType } from "$lib/schemas";

  export let rooms: RoomType[] = [
    {
      building: "prédio 1",
      name: "sala 1",
      vacancy: 20,
    },
    {
      building: "prédio 2",
      name: "sala 2",
      vacancy: 30,
    },
  ];

  function id(room: RoomType) {
    return `${room.building.replaceAll(" ", "-")}-${room.name.replaceAll(" ", "-")}`;
  }
</script>

<h1>Escolha a sala reserva</h1>
{#if rooms.length > 0}
  <form use:enhance action="?/allocate-room" method="post">
    <fieldset>
      <legend>Salas disponíveis</legend>
      {#each rooms as room}
        <div class="form-check">
          <input type="radio" class="form-check-input" name="room" id={id(room)} />
          <label for={id(room)} class="form-check-label">
            {room.name} | {room.building} | {room.vacancy} |
          </label>
        </div>
      {/each}
    </fieldset>

    <button type="submit" class="btn btn-primary">Efetuar reserva</button>
  </form>
{:else}
  <p>Não há salas disponíveis.</p>
{/if}
