import React, { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import c from 'classnames'
import type { AxiosError } from 'axios'
import type { HttpResponse } from '../lib/Http.tsx'
import { http } from '../lib/Http.tsx'
import { statusToColor, statusToMessage } from '../lib/SubmissionUtils.ts'
import { useLayout } from '../hooks/useLayout.ts'
import { toFixedNumber } from '../lib/misc.ts'
import { mdit } from '../lib/mdit.ts'

export const RecordPage: React.FC = () => {
  const { id } = useParams()
  const [data, setData] = useState<Submission>()
  const [score, setScore] = useState<number>(0)
  const { isMobile } = useLayout()
  useEffect(() => {
    void http.get<Submission>(`/submission/${id}`)
      .then((res) => {
        const data = res.data.data
        setData(data)
        if (data.testcaseResult && !!data.testcaseResult.length) {
          const score = data.testcaseResult.reduce((acc, cur) => {
            if (cur.status === 'ACCEPTED') {
              return acc + 1
            }
            return acc
          }, 0)
          setScore(100 * score / data.testcaseResult.length)
        }
      })
      .catch((err: AxiosError<HttpResponse>) => {
        throw err
      })
  }, [id])

  return (
    <div>
      <div className={c('max-w-[1200px] mx-auto my-2 flex items-start', isMobile && 'flex-col')}>
        <div className={c('bg-white rounded-lg shadow-md pb-8 my-1', isMobile ? 'w-11/12 mx-auto' : 'mx-2 w-3/4')}>
          <div className="px-4 pt-8">
            <h2 className="font-light">
              <span style={{ color: statusToColor(data?.status ?? 'PENDING') }}>
                {statusToMessage(data?.status ?? 'PENDING')}
              </span>
            </h2>
          </div>
          <div>
            <div className="flex flex-col gap-1 px-4">
              {data?.compileLog && (
                <div>
                  <pre>
                    {data?.compileLog}
                  </pre>
                </div>
              )}
              <div>
                {/* TODO: 重构为组件 */}
                <div dangerouslySetInnerHTML={{ __html: mdit.render(`\`\`\`\n${data?.code}\n\`\`\``) }}></div>
              </div>
              <div>
                {!!data?.testcaseResult?.length && (
                  <table className="w-full border-1">
                    <thead>
                      <tr className="text-gray-500 leading-8">
                        <th className="px-2 text-left">#</th>
                        <th className="px-2 text-left">状态</th>
                        <th className="px-2 text-left">分数</th>
                        <th className="px-2 text-left">时间</th>
                        <th className="px-2 text-left">内存</th>
                      </tr>
                    </thead>
                    <tbody>
                      {data?.testcaseResult?.map((item, index) => {
                        return (
                          <tr key={index} className="leading-6">
                            <td className="px-2 text-left">
                              #
                              {index + 1}
                            </td>
                            <td className="px-2 text-left">
                              <span style={{ color: statusToColor(item.status) }}>
                                {statusToMessage(item.status)}
                              </span>
                            </td>
                            <td className="px-2 text-left">{item.status === 'ACCEPTED' ? toFixedNumber(100 / (data?.testcaseResult?.length ?? 100)) : 0}</td>
                            <td className="px-2 text-left">
                              {item.time}
                              ms
                            </td>
                            <td className="px-2 text-left">
                              {item.memory}
                              Kib
                            </td>
                          </tr>
                        )
                      })}
                    </tbody>
                  </table>
                )}
              </div>
            </div>
          </div>
        </div>
        <div className={c('bg-white rounded-lg shadow-md pb-8 my-1', isMobile ? 'w-11/12 mx-auto' : 'mx-2 w-1/4')}>
          <div className="px-4 pt-8">
            <h2 className="font-light">信息</h2>
          </div>
          <div className="flex flex-col gap-4 px-4">
            <div className="flex flex-col gap-2">
              <div className="flex justify-between">
                <span className="text-gray-500">提交者</span>
                <span className="text-gray-500">
                  <Link to={`/u/${data?.user.username}`}>
                    {data?.user.username}
                  </Link>
                </span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-500">提交时间</span>
                <span className="text-gray-500">{data?.submitTime}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-500">语言</span>
                <span className="text-gray-500">{data?.language.languageName}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-500">题目</span>
                <span className="text-gray-500">
                  <Link to={`/p/${data?.problem.alias}`}>{data?.problem.title}</Link>
                </span>
              </div>
            </div>
            <div className="flex flex-col gap-2">
              <div className="flex justify-between">
                <span className="text-gray-500">最大内存</span>
                <span className="text-gray-500">
                  {data?.memory}
                  Kib
                </span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-500">最大时间</span>
                <span className="text-gray-500">
                  {data?.time}
                  ms
                </span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-500">分数</span>
                <span className="text-gray-500">{toFixedNumber(score)}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default RecordPage
