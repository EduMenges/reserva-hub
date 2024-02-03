<script lang="ts">
    import { hours } from "$lib/utils";

    export let startTime: Date;
    export let endTime: Date;
</script>

<td>
    {hours(startTime)} — {hours(endTime)}
</td>
