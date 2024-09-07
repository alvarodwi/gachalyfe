export default function InventoryArmsPage() {
  return (
    <>
      <h1>Manufacturer Arms</h1>

      <h2>Input today's manufacturer arms</h2>
      <form>
        <input type="number" name="amount" />
        <button>Submit</button>
      </form>

      <h2>Last 10 Manufacturer Arms</h2>
      <table>
        <tr>
          <th>Date</th>
          <th>Manufacturer</th>
          <th>Class</th>
          <th>Slot</th>
        </tr>
      </table>
    </>
  );
}
