export default function SpecialPage() {
  return (
    <>
      <h1>Special Interceptions</h1>

      <h2>Input today's attempt</h2>
      <form>
        <input type="date" name="date" />
        <select name="bossName">
          <option value="Alteisen">Alteisen</option>
          <option value="Gravedigger">Gravedigger</option>
          <option value="Blacksmith">Blacksmith</option>
          <option value="Chatterbox">Chatterbox</option>
          <option value="Modernia">Modernia</option>
        </select>
        <input type="number" name="t9equipment" />
        <input type="number" name="modules" />
        <input type="number" name="t9manufacturer_equipment" />
        <button>Submit</button>
      </form>

      <h2>Last 10 Attempts</h2>
      <table>
        <tr>
          <th>Date</th>
          <th>Boss Name</th>
          <th>T9 Equipments</th>
          <th>Modules</th>
          <th>T9 Manufacturer Equipments</th>
        </tr>
      </table>
    </>
  )
}
