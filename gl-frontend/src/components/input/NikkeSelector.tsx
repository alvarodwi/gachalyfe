import useApi from '@api/services/rapi'
import { NikkeItem } from '@models/domain/Nikke'
import cx from 'classix'
import { useEffect, useState } from 'react'

interface Props {
  onChange: (selected: NikkeItem[]) => void
  maxSelected: number
  className?: string
}

export default function NikkeSelector(props: Props) {
  const api = useApi().nikke

  const [searchTerm, setSearchTerm] = useState('')
  const [isOpen, setIsOpen] = useState(false)
  const [selectedNikke, setSelectedNikke] = useState<NikkeItem[]>([])
  const [nikkeList, setNikkeList] = useState<NikkeItem[]>([])

  useEffect(() => {
    setIsOpen(searchTerm != '')
  }, [searchTerm])

  useEffect(() => {
    async function searchNikke(name?: string) {
      const response = await api.search(name)
      if (response.status == 200) {
        setNikkeList(response.data ?? [])
      } else {
        console.error(response.message)
      }
    }
    searchNikke(searchTerm)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [searchTerm, setNikkeList])

  useEffect(() => {
    props.onChange(selectedNikke)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [selectedNikke])

  function handleSelect(nikke: NikkeItem) {
    setIsOpen(false)
    setSearchTerm('')
    if (selectedNikke.length >= props.maxSelected) {
      alert('Nikke pulled does not match total SSR')
    } else {
      setSelectedNikke((prevItem) => [...prevItem, nikke])
    }
  }

  function handleUnselectNikke(index: number) {
    setSelectedNikke((prevItem) => {
      const newItems = [...prevItem]
      newItems.splice(index, 1)
      return newItems
    })
  }

  return (
    <div className={cx(props.className, 'relative flex flex-col gap-2')}>
      <input
        type="text"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        placeholder="Add nikke by name"
        className="w-fit border-black p-2"
      />
      {isOpen && (
        <ul className="absolute left-full right-0 top-0 ml-4 max-h-48 w-full overflow-y-auto border border-black bg-white text-sm shadow-lg">
          {nikkeList.length > 0 ? (
            nikkeList.map(
              (nikke, i) =>
                i < 5 && (
                  <li
                    key={nikke.id}
                    onClick={() => handleSelect(nikke)}
                    className="cursor-pointer border-b border-gray-200 p-2"
                  >
                    {nikke.name}
                  </li>
                )
            )
          ) : (
            <li className="border-b border-gray-200 p-2">No nikke found</li>
          )}
        </ul>
      )}
      <div className="flex flex-row flex-wrap gap-2">
        {selectedNikke.length > 0 &&
          selectedNikke.map((nikke, i) => (
            <div
              key={i}
              className="flex min-w-[10ch] flex-row items-center justify-between border border-black px-2"
              onClick={() => handleUnselectNikke(i)}
            >
              <p className="line-clamp-1 max-w-[10ch] text-ellipsis">
                {nikke.name}
              </p>
              <span className="i-tabler-x" />
            </div>
          ))}
      </div>
    </div>
  )
}
