export default function AnomalyPage() {
  return (
    <>
      <h1>Anomaly Interceptions</h1>

      <h2>Input today's attempt</h2>
      <form>
        <input type="date" name="date" />
        <select name="bossName">
          <option value="Harvester">Harvester</option>
          <option value="Kraken">Kraken</option>
          <option value="Mirror Container">Mirror Container</option>
          <option value="Indivilia">Indivilia</option>
          <option value="Ultra">Ultra</option>
        </select>
        <input type="number" name="stage" />
        <select name="dropType">
          <option value="Boots">Boots</option>
          <option value="Modules">Modules</option>
          <option value="Gloves">Gloves</option>
          <option value="Torso">Torso</option>
          <option value="Helmet">Helmet</option>
        </select>
        <input type="checkbox" name="dropped" />
        <input type="number" name="modules" />
        <button>Submit</button>
      </form>

      <h2>Last 10 Attempts</h2>
      <table>
        <tr>
          <th>Date</th>
          <th>Boss Name</th>
          <th>Stage</th>
          <th>Drop Type</th>
          <th>Is Dropped</th>
          <th>Modules</th>
        </tr>
      </table>
    </>
  );
}
