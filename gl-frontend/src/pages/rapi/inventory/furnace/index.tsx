export default function InventoryFurnacePage() {
  return (
    <>
      <h1>Manufacturer Furnace</h1>

      <h2>Input today's manufacturer furnace</h2>
      <form>
        <input type="number" name="amount" />
        <button>Submit</button>
      </form>

      <h2>Last 10 Manufacturer Furnace</h2>
      <table>
        <tr>
          <th>Date</th>
          <th>Manufacturer</th>
          <th>Class</th>
          <th>Slot</th>
          <th>Sacrificed Equipment</th>
        </tr>
      </table>
    </>
  )
}
