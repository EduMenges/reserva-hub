<script lang="ts">
  import { currentDay } from "$lib/utils";

  let startTime: string;
</script>

<div class="alert alert-warning mb-3" role="alert">
  Reservas realizadas por alunos necessitam da aprovação de um administrador.
</div>

<form method="get" action="?/search">
  <fieldset name="horario">
    <legend>Horário</legend>

    <div class="row">
      <div class="mb-3 col-12 col-sm-6">
        <label for="date" class="form-label">Data</label>
        <input type="date" class="form-control" name="date" id="date" min={currentDay()} required/>
      </div>

      <div class="mb-3 col">
        <label for="time-start" class="form-label">Início</label>
        <input type="time" class="form-control" name="time-start" id="time-start" required bind:value={startTime}/>
      </div>

      <div class="mb-3 col">
        <label for="time-end" class="form-label">Fim</label>
        <input type="time" class="form-control" name="time-end" id="time-end" required min={startTime}/>
      </div>
    </div>
  </fieldset>

  <fieldset name="informacoes">
    <legend>Informações</legend>

    <div class="mb-3">
      <label for="title" class="form-label">Título do evento</label>
      <input type="text" name="title" id="title" class="form-control" required />
    </div>

    <div class="mb-3">
      <label for="description" class="form-label">Descrição</label>
      <textarea name="description" id="description" class="form-control" required></textarea>
    </div>
  </fieldset>

  <button type="submit" class="btn btn-primary">
    <i class="bi bi-search me-2" />
    Buscar salas
  </button>
</form>
