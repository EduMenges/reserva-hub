<script lang="ts">
    import { BootstrapColors } from "$lib/ApiTypes.d";

  export let textColor: BootstrapColors = BootstrapColors.PRIMARY;
  let className: string = "";
  export { className as class };
</script>

<h1 class={className}>Reserva<span class={`ms-1 rounded p-1 bg-${textColor} text-light`}>Hub</span></h1>
