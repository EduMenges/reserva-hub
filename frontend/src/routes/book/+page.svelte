<script lang="ts">
  import { currentDay } from "$lib/utils";
</script>

<div class="alert alert-warning" role="alert">
  Reservas realizadas por alunos necessitam da aprovação de um administrador.
</div>

<form method="get" action="?/search">
  <div class="mb-3">
    <label for="date" class="form-label">Data</label>
    <input type="date" class="form-control" name="date" id="date" min={currentDay()} required />
  </div>

  <fieldset name="Horário" class="grid" style="row-gap: 1px">
    <legend style="grid-row: 1;">Horário</legend>

    <div class="mb-3 g-col-6" style="grid-row: 2">
      <label for="time-start" class="form-label">Início</label>
      <input type="time" class="form-control" name="time-start" id="time-start" required />
    </div>

    <div class="mb-3 g-col-6" style="grid-row: 2">
      <label for="time-end" class="form-label">Fim</label>
      <input type="time" class="form-control" name="time-end" id="time-end" required />
    </div>
  </fieldset>

  <div class="mb-3">
    <label for="title" class="form-label">Título do evento</label>
    <input type="text" name="title" id="title" class="form-control" required />
  </div>

  <div class="mb-3">
    <label for="description" class="form-label">Descrição</label>
    <textarea name="description" id="description" cols="30" rows="10" class="form-control" required></textarea>
  </div>

  <button type="submit" class="btn btn-primary">
    <i class="bi bi-search" />
    Buscar salas
  </button>
</form>
