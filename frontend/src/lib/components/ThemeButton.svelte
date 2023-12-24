<script lang="ts">
  enum Options {
    Auto,
    Light,
    Dark,
  }

  let selected = Options.Auto;
</script>

<div class="nav-item dropdown">
  <button class="btn btn-secondary dropdown-toggle" data-bs-toggle="dropdown">
    Clique.
    <ul class="dropdown-menu">
      <li><button class="dropdown-item" type="button">☀️ Claro</button></li>
      <li><button class="dropdown-item" type="button">🌒 Escuro</button></li>
      <li>
        <button class="dropdown-item" type="button">🌗 Automático</button>
      </li>
    </ul>
  </button>
</div>

<div class="dropdown">
  <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
    Dropdown
  </button>
  <ul class="dropdown-menu">
    <li><button class="dropdown-item" type="button">Action</button></li>
    <li><button class="dropdown-item" type="button">Another action</button></li>
    <li><button class="dropdown-item" type="button">Something else here</button></li>
  </ul>
</div>

<!-- 
on:click={() => {
  const doc = document.querySelector("html");
  doc?.setAttribute("data-bs-theme", "light");
}}>Click</button -->
