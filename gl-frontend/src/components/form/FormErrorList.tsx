import { FieldErrors } from 'react-hook-form'

interface Props {
  errors: FieldErrors
}

export default function FormErrorList(props: Props) {
  return (
    <>
      {Object.keys(props.errors).length > 0 && (
        <div className="flex w-full flex-col">
          <h3 className="font-bold text-red-700">Form Errors:</h3>
          <ul className="text-sm text-gray-600">
            {Object.entries(props.errors).map(([field, error], index) => (
              <li className="flex flex-row gap-1" key={index}>
                <span className="text-red">• {field}</span>:
                <span>
                  {typeof error?.message === 'string'
                    ? error.message
                    : 'unknown error'}
                </span>
              </li>
            ))}
          </ul>
        </div>
      )}
    </>
  )
}
