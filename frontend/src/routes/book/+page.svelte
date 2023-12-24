<div class="alert alert-warning" role="alert">
  Reservas realizadas por alunos necessitam da aprovação de um administrador.
</div>

<form>
  <div class="mb-3">
    <label for="date" class="form-label">Data</label>
    <input
      type="datetime"
      class="form-control"
      name="date"
      id="date"
      required
    />
  </div>

  <div class="grid">
    <div class="mb-3 g-col-6">
      <label for="time-start" class="form-label">Horário de início</label>
      <input
        type="time"
        class="form-control"
        name="time-start"
        id="time-start"
        required
      />
    </div>
    <div class="mb-3 g-col-6">
      <label for="time-end" class="form-label">Horário de fim</label>
      <input
        type="time"
        class="form-control"
        name="time-end"
        id="time-end"
        required
      />
    </div>
  </div>

  <div class="mb-3">
    <label for="title" class="form-label">Título do evento</label>
    <input type="text" name="title" id="title" class="form-control" required />
  </div>

  <div class="mb-3">
    <label for="description" class="form-label">Descrição</label>
    <textarea
      name="description"
      id="description"
      cols="30"
      rows="10"
      class="form-control"
      required
    ></textarea>
  </div>

  <button type="submit" class="btn btn-primary">🔎 Buscar salas</button>
</form>
