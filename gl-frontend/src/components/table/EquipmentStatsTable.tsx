import { EquipmentStats } from '@models/domain/stats/EquipmentStats'

interface Props {
  data: EquipmentStats
}

export default function EquipmentStatsTable(props: Props) {
  const stats = props.data.equipmentStatsByCategory

  const manufacturers = ['Elysion', 'Missilis', 'Tetra', 'Pilgrim', 'Abnormal']
  const classTypes = ['Attacker', 'Defender', 'Supporter']
  const slotTypes = ['Helmet', 'Torso', 'Gloves', 'Boots']

  return (
    <table className="table-auto text-sm">
      <thead>
        <tr>
          <th className="p-2" colSpan={2} />
          {slotTypes.map((slotType) => (
            <th key={slotType} className="border border-black p-2">
              {slotType}
            </th>
          ))}
        </tr>
      </thead>
      <tbody>
        {manufacturers.map((manufacturer) =>
          classTypes.map((classType, index) => (
            <tr key={`${manufacturer}-${classType}`}>
              {index === 0 && (
                <td
                  rowSpan={classTypes.length}
                  className="border border-black p-2 text-center font-bold"
                >
                  {manufacturer}
                </td>
              )}
              <td className="border border-black p-2 text-center font-bold">
                {classType}
              </td>
              {slotTypes.map((slotType) => {
                const stat = stats.find(
                  (item) =>
                    item.manufacturer === manufacturer &&
                    item.classType === classType &&
                    item.slotType === slotType
                )
                return (
                  <td
                    key={slotType}
                    className="border border-black p-2 text-end text-base"
                  >
                    {stat ? stat.total : 0}
                  </td>
                )
              })}
            </tr>
          ))
        )}
      </tbody>
    </table>
  )
}
